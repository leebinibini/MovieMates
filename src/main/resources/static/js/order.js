document.addEventListener("DOMContentLoaded", function () {
    $(".btn-ticket").on('click', function(e) {
        var movieId = $(e.target).data('id');
        axios.get(`/api/movie/order/${movieId}`)
            .then(function (res) {
                var theaterList = res.data.theater;
                var scheduleList = res.data.schedule;
                alert(scheduleList);
                var title = res.data.title;


                var locationSelect = $("select[name='location']");
                locationSelect.empty();
                theaterList.forEach(function (theater) {
                    locationSelect.append(new Option(theater.name, theater.id));
                });
                $('#movie-selection').val(title);

                var dateInput = document.querySelector('.datetime');
                var availableDates = scheduleList.map(function(schedule) {
                    return new Date(schedule.showDate).toISOString().split('T')[0];
                });
                var today = new Date().toISOString().split('T')[0];
                dateInput.setAttribute('min', today);
                dateInput.setAttribute('max', availableDates[availableDates.length - 1]);
                dateInput.value = today;

                dateInput.addEventListener('change', function() {
                    var selectedDate = new Date(schedule.showDate).toISOString().split('T')[0];
                    if (availableDates.includes(selectedDate)) {
                        renderTimesForSelectedDate(selectedDate);
                    } else {
                        dateInput.value = '';
                        alert('해당 날짜에 상영 정보가 없습니다.');
                    }
                });

                function renderTimesForSelectedDate(selectedDate) {
                    var filteredSchedules = scheduleList.filter(function(schedule) {
                        var scheduleDate = new Date(schedule.showDate).toISOString().split('T')[0];
                        return scheduleDate === selectedDate;
                    });
                    var timeList = $(".order-date");
                    timeList.empty();
                    if (filteredSchedules.length > 0) {
                        filteredSchedules.forEach(function(schedule) {
                            var time = new Date(schedule.showTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
                            timeList.append('<li><a href="javascript:;" data-time="' + time + '"><i>' + time + '</i></a></li>');
                        });
                    } else {
                        timeList.append('<li><a href="javascript:;"><i>해당 날짜에 상영 시간이 없습니다.</i></a></li>');
                    }
                }
                renderTimesForSelectedDate(today);

                var timeList = $(".order-date");
                timeList.empty();
                scheduleList.forEach(function (schedule) {
                    var time = new Date(schedule.showTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
                    timeList.append('<li><a href="javascript:;" data-value="' + time + '" data-schedule-id="' + schedule.id + '"><i>' + time + '</i></a></li>');
                });


                $('.order-date').off('click').on('click', 'a', function() {
                    // 시간을 HH:mm 형식으로 변환
                    var selectedTime = new Date(schedule.showTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });

                    $('.order-date a').removeClass('selected');
                    $(this).addClass('selected');


                    var selectedLocation = $('select[name="location"]').val();
                    var selectedMovie = $('#movie-selection').val();
                    var selectedDate = $('.datetime').val();
                    sessionStorage.setItem('selectedLocation', selectedLocation);
                    sessionStorage.setItem('selectedMovie', selectedMovie);
                    sessionStorage.setItem('selectedDate', selectedDate);
                    sessionStorage.setItem('selectedTime', selectedTime);

                    alert('Selected Time:', selectedTime);
                    alert('Selected Location:', selectedLocation);
                    alert('Selected Movie:', selectedMovie);
                    alert('Selected Date:', selectedDate);

                    alert('선택된 영화관: ' + selectedLocation +
                        ' 선택된 영화: ' + selectedMovie +
                        ' 선택된 날짜: ' + selectedDate +
                        ' 선택된 시간: ' + selectedTime);
                });
            })
            .catch(function (error) {
                console.log("error data:", error);
            });
    });
});

var price = 13000;
$(document).ready(function () {
    var $cart = $('#selected-seats'),
        $counter = $('#counter'),
        $total = $('#total');
    var sc;

    $('.next').on('click', function () {
        var selectedLocation = sessionStorage.getItem('selectedLocation');
        var selectedMovie = sessionStorage.getItem('selectedMovie');
        var selectedDate = sessionStorage.getItem('selectedDate');
        var selectedTime = sessionStorage.getItem('selectedTime');

        var theaterId = 0;
        var movieId = 0;

        axios.get(`/api/theater/findTheaterIdByName?name=${encodeURIComponent(selectedLocation)}`)
            .then(function (theaterResponse) {
                theaterId = theaterResponse.data;

                return axios.get(`/api/movies/findMovieIdByName?name=${encodeURIComponent(selectedMovie)}`);
            })
            .then(function (movieResponse) {
                movieId = movieResponse.data.id;

                return $.ajax({
                    url: '/api/seat',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        theaterId: theaterId,
                        movieId: movieId,
                        location: selectedLocation,
                        movie: selectedMovie,
                        date: selectedDate,
                        time: selectedTime
                    }),
                    success: function (response) {
                        updateSeatMap(response.seats);
                    },
                    error: function (error) {
                        console.error("좌석 데이터 전송 오류:", error);
                    }
                });
            })
            .catch(function (error) {
                console.error("에러 발생:", error);
            });

        function updateSeatMap(seats) {
            if (sc) {
                $('#seat-map').empty();
            }

            sc = $('#seat-map').seatCharts({
                map: [
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa'
                ],
                naming: {
                    top: false,
                    getLabel: function (character, row, column) {
                        return column;
                    }
                },
                legend: {
                    node: $('#legend'),
                    items: [
                        ['a', 'available', 'Available'],
                        ['a', 'unavailable', 'Unavailable'],
                        ['a', 'selected', 'Selected'],
                    ]
                },
                click: function () {
                    if (this.status() == 'available') {
                        $('<li>R' + (this.settings.row + 1) + ' S' + this.settings.label + '</li>')
                            .attr('id', 'cart-item-' + this.settings.id)
                            .data('seatId', this.settings.id)
                            .appendTo($cart);

                        $counter.text(sc.find('selected').length);
                        $total.text(recalculateTotal(sc));

                        return 'selected';
                    } else if (this.status() == 'selected') {
                        $counter.text(sc.find('selected').length - 1);
                        $total.text(recalculateTotal(sc));
                        $('#cart-item-' + this.settings.id).remove();
                        return 'available';
                    } else if (this.status() == 'unavailable') {
                        return 'unavailable';
                    } else {
                        return this.style();
                    }
                }
            });

            seats.forEach(function (seat) {
                if (seat.status === 'unavailable') {
                    sc.get([seat.row + '_' + seat.column]).status('unavailable');
                }
            });
        }

        function recalculateTotal(sc) {
            var total = 0;
            sc.find('selected').each(function () {
                total += price;
            });
            return total;
        }
    });
});