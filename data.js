import axios from 'axios';

const randint = (min, max) => {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min;
}

const solveRequest = () => {
    let cnt = 400;
    while (cnt--) {
        axios({
            url: "http://oracle.wpl.kro.kr:8080/api/v0/quiz/solve",
            method: "post",
            data: {
                userId: randint(3, 9),
                quizId: randint(4, 44),
                answer: randint(1, 5),
            },
        }).then((response) => {
            console.log(response);
        });

    }

}

solveRequest();