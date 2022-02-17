let CHECK_STATUS = false;

async function checkDupleLoginId(){

    let inputLoginId = document.querySelector("#loginId");
    let loginId = inputLoginId.value;

    await fetch("http://localhost:8085/member/check/id?loginId=" + loginId)
    .then(

        (response) => {
            return response.json();
        }
    )
    .then(

        (data) => {

            let idCheck = data;

            console.log("idCheck")
            console.log(idCheck);
            console.log("idCheck.status")
            console.log(idCheck.status);

            

        }

    )
    .catch(
        (error) => {
            console.log(error);
        }
    )

}