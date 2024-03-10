let intro = document.querySelector('.intro');
let welcome = document.querySelector('.welcome');
let messagespan = document.querySelectorAll('.message');

window.addEventListener('DOMContentLoaded', ()=>{

    setTimeout(()=>{

        messagespan.forEach((span, idx)=>{
            setTimeout(()=>{
                span.classList.add('active');
            }, (idx + 1) * 400)
        });

        setTimeout(()=>{
            messagespan.forEach((span, idx)=>{

                setTimeout(()=>{
                    span.classList.remove('active');
                    span.classList.add('fade');
                }, (idx + 1) * 50)
            })
        },2000);

        setTimeout(()=>{
            intro.style.top = '-100vh';
        },2300)

    })
})