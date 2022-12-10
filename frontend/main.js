const makeProblemList = () => {
    fetch("http://oracle.wpl.kro.kr:8080/api/v0/quiz")
    .then((res) => res.json())
    .then((data) => {

        

        for(let i=0;i<data.data.length;i++){
            let div1 = document.createElement("div")
            div1.className="card"
            div1.style="width: 18rem;"

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
            but.href='#'
            but.className="btn btn-primary"
            but.innerHTML='Try'
            div2.appendChild(but)

            document.body.appendChild(div1)
            // <div class="card" style="width: 18rem;">
            //     <div class="card-body">
            //     <h5 class="card-title">Card title</h5>
            //     <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
            //     <a href="#" class="btn btn-primary">Go somewhere</a>
            //     </div>
            // </div>
        }
        console.log(data.data[0])
        console.log(data.data.length)
    })
    
    
}

const makeRanking = () => {

}