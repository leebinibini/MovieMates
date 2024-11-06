document.addEventListener("DOMContentLoaded", function () {
    IMP.init('imp01544136');
    var theaterId = 0;
    var sc = {
        find: function (status) {
            // 'selected' 좌석을 하드코딩된 형태로 반환
            return [
                { row: 1, column: 1, price: 13000 },
                { row: 1, column: 2, price: 13000 }
            ];
        }
    };;
    var selectedSeats = [];



    function recalculateTotal(sc) {
        var total = 0;

        if (sc && sc.find) {
            // 'selected' 좌석의 가격을 합산
            sc.find('selected').forEach(function (seat) {
                total += seat.price;
            });
        } else {
            console.error("sc가 정의되지 않았거나, 'find' 메서드를 사용할 수 없습니다.");
        }

        return total;  // 총 가격을 반환
    }

    $(".btn-ticket").on('click', function (e) {
        movieId = $(this).data('id');  // 여기서 movieId 값을 저장
        if (!movieId) {
            console.error("movieId가 설정되지 않았습니다.");
            return;
        }
        axios.get(`/api/movie/order/${movieId}`)
            .then(function (res) {
                console.log(res.data);
                var theaterList = res.data.theater;
                var scheduleList = res.data.schedule;

                if (!scheduleList || scheduleList.length === 0) {
                    console.error("스케줄 데이터가 없습니다.");
                    alert("스케줄 데이터가 없습니다.");
                    return;
                }

                var title = res.data.title;
                var locationSelect = $("select[name='location']");
                locationSelect.empty();
                locationSelect.append('<option value="">영화관을 선택하세요</option>');
                theaterList.forEach(function (theater) {
                    locationSelect.append(new Option(theater.name, theater.id));
                });
                $('#movie-selection').val(title).data('id', movieId);

                var timeList = $(".order-date");
                timeList.empty();
                timeList.append('<li><a href="javascript:;"><i>날짜를 선택해주세요</i></a></li>');

                locationSelect.on('change', function () {
                    theaterId = $(this).val();  // theaterId를 저장
                    var selectedTheaterName = $(this).find('option:selected').text();
                    $('#locationp').text(selectedTheaterName);

                    if (!theaterId) {
                        alert('극장을 선택해주세요.');
                        return;
                    }

                    var dateInput = document.querySelector('.datetime');
                    var availableDates = scheduleList
                        .filter(schedule => schedule.theaterId == theaterId)
                        .map(function (schedule) {
                            return new Date(schedule.showDate).toISOString().split('T')[0];
                        });

                    var today = new Date().toISOString().split('T')[0];
                    dateInput.setAttribute('min', today);
                    dateInput.setAttribute('max', availableDates[availableDates.length - 1]);
                    dateInput.value = today;

                    dateInput.addEventListener('change', function () {
                        var selectedDate = new Date(this.value).toISOString().split('T')[0];
                        $('#datep').text(selectedDate);

                        if (availableDates.includes(selectedDate)) {
                            renderTimesForSelectedDate(theaterId, selectedDate, scheduleList);
                        } else {
                            timeList.empty();
                            timeList.append('<li><a href="javascript:;"><i>해당 날짜에 상영 정보가 없습니다.</i></a></li>');
                        }
                    });

                    renderTimesForSelectedDate(theaterId, today, scheduleList);
                });

                function renderTimesForSelectedDate(theaterId, selectedDate, scheduleList) {
                    var filteredSchedules = scheduleList.filter(function (schedule) {
                        var scheduleDate = new Date(schedule.showDate).toISOString().split('T')[0];
                        return schedule.theaterId == theaterId && scheduleDate === selectedDate;
                    });

                    timeList.empty();

                    if (filteredSchedules.length > 0) {
                        filteredSchedules.forEach(function (schedule) {
                            var time = schedule.showTime;
                            timeList.append('<li><a href="javascript:;" data-time="' + time + '" data-schedule-id="' + schedule.id + '"><i>' + time + '</i></a></li>');
                        });
                    } else {
                        timeList.append('<li><a href="javascript:;"><i>해당 날짜에 상영 시간이 없습니다.</i></a></li>');
                    }

                    $('.order-date').off('click').on('click', 'a', function () {
                        var selectedTime = $(this).data('time');
                        var selectedDate = $('.datetime').val();
                        var dateTimeText = selectedDate + ' ' + selectedTime;
                        var selectedSeats = $("#selected-seats").val();
                        alert(selectedSeats)

                        $('#timep').text(dateTimeText);
                        $('.order-date a').removeClass('selected');
                        $(this).addClass('selected');

                        var selectedLocation = $('select[name="location"]').val();
                        var selectedMovie = $('#movie-selection').val();
                        sessionStorage.setItem('selectedLocation', selectedLocation);
                        sessionStorage.setItem('selectedMovie', selectedMovie);
                        sessionStorage.setItem('selectedDate', selectedDate);
                        sessionStorage.setItem('selectedTime', selectedTime);
                        alert('선택한 시간: '+ dateTimeText);
                    });
                }
            })
            .catch(function (error) {
                console.log("error data:", error);
            });
    });
    $(document).ready(function () {
        $('.submit').on('click', async function () {


            // var selectedLocation = "gangnam";        // 영화관
            // var selectedMovie = "대도시의 사랑법";  // 영화 제목
            // var selectedDate = "2024-11-29";         // 상영 날짜
            // var selectedTime = "12:30";              // 상영 시간
            // var totalPrice = 26000;                  // 총 결제 금액
            // var seats = [
            //     {row: 1, column: 1},
            //     {row: 1, column: 2}
            // ];  // 선택된 좌석 (하드코딩)
            // var scheduleId = 1; // 스케줄 ID (하드코딩)
            // var userId = 29;
            // var seatId = 2;

            if (totalPrice === 0) {
                alert("결제 금액이 0원입니다. 좌석을 선택해주세요.");
                return;  // 결제 금액이 0원이면 결제를 중지
            }

            IMP.request_pay({
                pg: "danal_tpay",          // PG사 설정
                pay_method: "card",        // 결제 방법
                name: selectedMovie,       // 상품 이름 (영화 제목)
                amount: totalPrice,        // 결제 금액 (총 티켓 가격)
                custom_data: {             // 커스텀 데이터
                    location: selectedLocation,
                    movie: selectedMovie,
                    date: selectedDate,
                    time: selectedTime,
                    seatId: seatId,
                    scheduleId: scheduleId
                }
            }, async function (res) {
                if (res.success) {
                    try {
                        var {location, movie, date, time, seatId, scheduleId} = res.custom_data;
                        var response = await axios.post(`/api/payments/validation/${res.imp_uid}`, {
                            location, movie, userId, seatId, scheduleId
                        });
                        alert("결제가 되었습니다! 마이페이지가서 확인해주세요:)")
                    } catch (error) {
                        alert("결제에 실패하였습니다, 다시 시도해주세요!");
                        console.error("Payment validation failed:", error.response.data);
                    }
                    // 예약과 결제 저장은 나중에 처리
                } else {
                    // 결제가 실패했을 때
                    alert("결제에 실패했습니다. 다시 시도해주세요!");
                    console.error("Payment failed:", res.error_msg);
                }
            });
        })
    })




});