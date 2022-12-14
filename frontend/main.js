const makeProblemList = () => {

    let solves = []

    fetch("http://oracle.wpl.kro.kr:8080/api/v0/user/"+localStorage.getItem('id').toString())
    .then((res)=> res.json())
    .then((data)=>{
        console.log(data.data.solves)
        solves = data.data.solves
    })


    fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz")
    .then((res) => res.json())
    .then((data) => {
        console.log(data)
        for(let i=0;i<data.data.length;i++){
            let ck = 0
            // console.log(data.data[i].quizId)
            solves.forEach(x => {
                // console.log(x)
                if(x.quizId===data.data[i].quizId){
                    console.log(1)
                    if(x.status==='SUCCESS'){
                        ck=1
                    }
                    else if(ck===0){
                        ck=2
                    }
                }
            })

            let div1 = document.createElement("div")
            div1.className="card mb-3"
            div1.style="width: 25%;"

            let div2 = document.createElement("div")
            div2.className="card-body"
            div1.appendChild(div2)

            let title = document.createElement("h5")
            title.className="card-title"
            title.innerHTML=data.data[i].title
            div2.appendChild(title)

            let desc = document.createElement("p")
            desc.className="card-text"
            desc.innerHTML=data.data[i].description
            div2.appendChild(desc)

            let but = document.createElement('a')
            but.href='./problem.html?pid='+data.data[i].quizId
            but.className="btn " +(ck===0 ? "btn-primary":(ck===1?"btn-success":"btn-danger"))
            but.innerHTML='Try ' +(ck===0 ? "":(ck===1?"(PASS)":"(FAIL)"))
            div2.appendChild(but)

            let x = document.querySelector('.row-cols-4')
            x.appendChild(div1)
        }
        console.log(location.href.split('/')[4])
    })
    
    
}

const makeRanking = () => {
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/user")
    .then((res) => res.json())
    .then((data) => {

        let rank=[]
        data.data.forEach(x=>{
            let correct=[]
            x.solves.forEach(y => {
                if(y.status==='SUCCESS' && (!correct.includes(y.quizId))){
                    correct.push(y.quizId)
                }
            })
            rank.push({
                name: x.email,
                cnt: correct.length
            })
        })

        rank.sort((a,b)=>{
            return b.cnt-a.cnt
        })

        let num=1
        rank.forEach(x => {
            let li = document.createElement("li")
            li.className="list-group-item d-flex justify-content-between align-items-center"
            li.innerHTML=num+'. '+x.name
            num+=1

            let span = document.createElement('span')
            span.className="badge bg-primary rounded-pill"
            li.appendChild(span)
            span.innerHTML=x.cnt
            document.querySelector('.list-group').appendChild(li)
            console.log(x)
        })
    })
}

const makeProblem = () => {
    const pid = location.href.split('=')[1]
    // const pid = 3

    fetch('http://oracle.wpl.kro.kr:8080/api/v0/quiz/'+pid.toString())
    .then((res) => res.json())
    .then((data) => {
        console.log(data.data)
        let body = document.querySelector('#body')

        let img = document.createElement('img')
        img.src=data.data.images[0]
        img.width="1000"

        body.appendChild(img)
        
        let c = 0

        for(let i=0;i<4;i++){   
            let div = document.createElement('div')
            div.className="form-check"
            let input = document.createElement('input')
            input.className="form-check-input"
            input.type="radio"
            input.name="radio"
            input.id=i+1
            input.value=i+1
            let label = document.createElement('label')
            label.className="form-check-label"
            label.htmlFor=i+1
            label.innerHTML=i+1

            input.addEventListener("click",() => {
                c = i+1
            })
            
            div.appendChild(input)
            div.appendChild(label)
            body.appendChild(div)
        }

        let but = document.createElement('button')
        but.className="btn btn-primary"
        but.type='submit'
        but.innerHTML="submit"
        but.addEventListener("click",()=>{
            if(c===0) return;
            console.log(localStorage.getItem("id"))
            fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz/solve", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                userId: localStorage.getItem("id"),
                quizId: pid,
                answer: c,
            }),
            }).then((response) => console.log(response));
            location.href='./history.html'
        })
        body.appendChild(but)

    })
}

const idToName = () => {
    const ret = {}
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/user")
    .then((res) => res.json())
    .then((data) => {
        for(let i=0;i<data.data.length;i++){
            ret[data.data[i].userId] = {
                name: data.data[i].name,
                email: data.data[i].email,
                id: data.data[i].userId,
            }
        }
    })
    return ret
}

const makeHistory = () => {

    const nameDict = idToName()
    let quizIdToTitle = {}
    console.log(nameDict)
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz")
    .then((res) => res.json())
    .then((data) => {
        let tdata=[]
        for(let i=0;i<data.data.length;i++){
            const solves = data.data[i].solves 
            quizIdToTitle[data.data[i].quizId] = data.data[i].title
            for(let j=0;j<solves.length;j++){
                tdata.push(solves[j])
            }
        }
        tdata.sort((a,b) => {
            return new Date(b.dateTime) - new Date(a.dateTime)
        })
        let cnt = tdata.length
        tdata.forEach(x => {
            let tr = document.createElement('tr')
            let th = document.createElement('th')
            th.scope = "row"
            th.innerHTML=cnt
            tr.appendChild(th)
            cnt--;
            let td = document.createElement('td')
            td.innerHTML=nameDict[x.userId].name
            tr.appendChild(td)
            td = document.createElement('td')
            td.innerHTML=quizIdToTitle[x.quizId]
            tr.appendChild(td)
            td = document.createElement('td')
            let span = document.createElement('span')
            span.className="badge "+ (x.status==="SUCCESS" ? "text-bg-success" : "text-bg-danger")
            span.innerHTML=x.status=="SUCCESS" ? "Correct!" : "Incorrect"
            td.appendChild(span)
            tr.appendChild(td)
            td = document.createElement('td')
            td.innerHTML=x.dateTime
            tr.appendChild(td)
            document.querySelector('tbody').appendChild(tr)

            
        })
        console.log(data.data)
    })
}


const loginPage = () => {
    let positionNow = 0
    let selectLogin = document.querySelector('#login')
    let selectRegister = document.querySelector('#register')
    let con = document.querySelector('.container-sm')

    login()

    selectRegister.addEventListener('click', () => {
        if(positionNow===1) return;
        positionNow=1

        let newDiv = document.createElement('div')
        newDiv.className='form-floating mb-3'
        let input = document.createElement('input')
        input.type='text'
        input.className='form-control'
        input.id='name'
        input.placeholder='name'
        let label = document.createElement('label')
        label.htmlFor='floatingInput'
        label.innerHTML='Name'
        newDiv.appendChild(input)
        newDiv.appendChild(label)

        con.insertBefore(newDiv,con.firstChild.nextSibling.nextSibling)

        let email = document.querySelector('#email')
        let password = document.querySelector('#password')
        let name = document.querySelector('#name')
        email.classList.remove('is-invalid')
        password.classList.remove('is-invalid')
        name.classList.remove('is-invalid')

        register()
    })
    selectLogin.addEventListener('click', () => {
        if(positionNow===0) return;
        positionNow=0
        con.removeChild(con.firstChild.nextSibling.nextSibling)
        let email = document.querySelector('#email')
        let password = document.querySelector('#password')
        email.classList.remove('is-invalid')
        password.classList.remove('is-invalid')

        login()
    })
}

const registerF = ()=>{
    let email = document.querySelector('#email')
    let password = document.querySelector('#password')
    let name = document.querySelector('#name')
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/user/join", {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
    },
    body: JSON.stringify({
        email: email.value,
        password: password.value,
        name: name.value,
    }),
    }).then((response) => {
        if(response.status!==200){
            alert('Invalid Register')
            email.classList.add('is-invalid')
            password.classList.add('is-invalid')
            name.classList.add('is-invalid')
        }
        else{
            let selectLogin = document.querySelector('#login')
            alert("Success Register!")
            selectLogin.click()
        }
        console.log(response.status)
    });
}

const loginF = ()=>{
    let k = idToName()
    let email = document.querySelector('#email')
    console.log(email.value)

    fetch("http://oracle.wpl.kro.kr:8080/api/v0/user/login", {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
    },
    body: JSON.stringify({
        email: email.value,
        password: password.value,
    }),
    }).then((response) => {
        if(response.status!==200){
            alert('Invalid Login')
            email.classList.add('is-invalid')
            password.classList.add('is-invalid')
        }
        else{
            return response.json();
        }
    }).then((data) => {
        localStorage.setItem('id', data.data.userId);
        localStorage.setItem('email', data.data.email);
        location.href = './problems.html'
    });
}
const login = () => {

    let but = document.querySelector('.p-3.mt-4.text-center')
    but.style="cursor: pointer;"
    but.innerHTML="Login"

    let password = document.querySelector('#password')

    password.addEventListener('keyup',(e) => {
        if(e.keyCode==13){
            console.log(password.nextSibling)
            but.click()
        }
    })

    
    but.removeEventListener('click',registerF)    
    but.addEventListener("click",loginF)
    
}

const register = () => {
    let but = document.querySelector('.p-3.mt-4.text-center')
    but.style="cursor: pointer;"
    but.innerHTML="Register"
    
    but.removeEventListener('click',loginF)    
    but.addEventListener("click",registerF)
}

const randomAll = () => {
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz")
    .then((res) => res.json())
    .then((data) => {
        let quizIdList = []
        console.log(quizIdList)
        for(let i=0;i<data.data.length;i++){
            quizIdList.push(data.data[i].quizId)
        }
        let rand = Math.floor(Math.random()*quizIdList.length)

        location.href='./problem.html?pid='+quizIdList[rand]
    })
}

const randomUnsolve = () => {
    const nameDict = idToName()
    
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz")
    .then((res) => res.json())
    .then((data) => {
        let quizIdList = []
        console.log(quizIdList)
        for(let i=0;i<data.data.length;i++){
            let ck=true
            
            data.data[i].solves.forEach(x => {
                if(x.status==='SUCCESS'){
                    if(nameDict[x.userId].email===localStorage.getItem('email')){
                        ck=false
                    }
                }
            })
            if(ck){
                quizIdList.push(data.data[i].quizId)
            }

        }
        let rand = Math.floor(Math.random()*quizIdList.length)

        location.href='./problem.html?pid='+quizIdList[rand]
    })
}

const pageInit = () => {
    console.log(localStorage.getItem("email"))
    if(localStorage.getItem("email")!==null){
        let email = document.createElement('button')
        email.className="btn btn-outline-dark"
        console.log(localStorage.getItem("email"))
        email.innerHTML=localStorage.getItem("email")
        document.querySelector(".d-flex").appendChild(email)
        let logout = document.createElement('button')
        logout.className="btn btn-primary"
        logout.innerHTML="Logout"
        logout.addEventListener("click",() => {
            localStorage.clear()
            location.href="./index.html"
        })
        document.querySelector(".d-flex").appendChild(logout)
    }
    else{
        location.href="./index.html"
    }
}
