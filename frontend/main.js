const makeProblemList = () => {
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz")
    .then((res) => res.json())
    .then((data) => {
        for(let i=0;i<data.data.length;i++){
            let div1 = document.createElement("div")
            div1.className="card"
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
            but.className="btn btn-primary"
            but.innerHTML='Try'
            div2.appendChild(but)

            let x = document.querySelector('.row-cols-4')
            x.appendChild(div1)
            // <div class="card" style="width: 18rem;">
            //     <div class="card-body">
            //     <h5 class="card-title">Card title</h5>
            //     <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
            //     <a href="#" class="btn btn-primary">Go somewhere</a>
            //     </div>
            // </div>
        }
        console.log(location.href.split('/')[4])
    })
    
    
}

const makeRanking = () => {
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/user")
    .then((res) => res.json())
    .then((data) => {
        for(let i=0;i<data.data.length;i++){
            let li = document.createElement("li")
            li.className="list-group-item"
            li.innerHTML=(i+1).toString()
            li.innerHTML+='. '+data.data[i].email

            document.querySelector('.list-group').appendChild(li)
        }
        console.log(data.data[0])
        console.log(data.data.length)
    })
}

const makeProblem = () => {
    // const pid = location.href.split('?')[1]
    const pid = 3

    fetch('http://oracle.wpl.kro.kr:8080/api/v0/quiz/'+pid.toString())
    .then((res) => res.json())
    .then((data) => {
        console.log(data.data)
        let body = document.querySelector('#body')

        let img = document.createElement('img')
        img.src=data.data.images[0]
        img.width="1000"

        body.appendChild(img)
        

        for(let i=0;i<5;i++){   
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
            
            div.appendChild(input)
            div.appendChild(label)
            body.appendChild(div)
        }

        let but = document.createElement('button')
        but.className="btn btn-primary"
        but.type='submit'
        but.innerHTML="submit"
        but.addEventListener("click",()=>{
            fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz/solve", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                userId: "test",
                quizId: pid,
                answer: $('input[name=radio]:checked').val(),
            }),
            }).then((response) => console.log(response));
        })
        body.appendChild(but)

    })
}

const makeHistory = () => {

}