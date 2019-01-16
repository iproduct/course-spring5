$(document).ready(function(){
    var api = "http://localhost:8080/api/";
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext('2d');
    var raf;

    var thor = new GameObject2D(ctx, 1, 'Thor');

    var thorImage = new Image();   // Create new img element
    thorImage.addEventListener('load', function() {
        thor.image = thorImage;
    }, false);
    thorImage.src = 'img/thor1.png'; // Set source path

    var backgroundImage = new Image();   // Create new img element
    backgroundImage.src = 'img/background1.jpg';

    function draw() {

        if(backgroundImage.width > 0) {
            ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);
        } else {
            ctx.clearRect(0,0, canvas.width, canvas.height);
        }
        thor.draw();
        raf = window.requestAnimationFrame(draw);
    }

    // canvas.addEventListener('mouseover', function(e) {
    //     raf = window.requestAnimationFrame(draw);
    // });
    //
    // canvas.addEventListener('mouseout', function(e) {
    //     window.cancelAnimationFrame(raf);
    // });

    registerListeners();
    draw();

    function registerListeners() {
        if (!!window.EventSource) {
            var eventSource = new EventSource(api + "game");

            eventSource.onmessage = function(e) {
                var gameObj = JSON.parse(e.data)
                console.log(gameObj);
                thor.x = gameObj.positionX;
                thor.y = gameObj.positionY;

                // var index = chart.series.findIndex(serie => serie.name == datapoint.pid);
                // if(index > 0) {
                //     var serie = chart.series[index];
                //     var shift = serie.data.length > 20;
                //     serie.addPoint([datapoint.instant, datapoint.load], true, shift);
                // }
                //
                // // request new processes info if processes have changed
                // if (datapoint.changed) {
                //     updateProcesses();
                // }
            };

            eventSource.addEventListener('open', function(event) {
                console.log('Opened: ', event);
            }, false);

            $('body').bind('unload', function() {
                eventSource.close();
                window.cancelAnimationFrame(raf);
            });

        }
    }

    function updateProcesses() {
        $.getJSON(api + "processes")
            .done(function(data) {
                var items = [];
                var series = [];
                data.forEach( info => {
                    items.push("<li><span class='pid'>PID: " + info.pid
                    + "</span> - <span class='command'>" + info.command + "</span></li>");
                var existing = chart.series.find( serie => serie.name === info.pid);
                console.log(existing);
                var existingData = [];
                if(existing) {
                    existingData = existing.data.map( point => ({x: point.x, y: point.y}));
                }
//					console.log(existingData);
                series.push( { name: info.pid, data: existingData } );
            });
                chart.update({series: series}, true, true);
                $("#processes").html(items.join(""));
            });
    }
});
