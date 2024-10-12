//유저 업데이트 api

// 입력한 유저의 정보 보내기
function update()  {
axios.post("api/user/update",{data})
    .then((res)=>res.data)
    .catch((err)=> console.log(err));
};
