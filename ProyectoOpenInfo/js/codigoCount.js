$(function(){    
    $('#countdown').countdown({
        timezone:-4, //zona horaria bolivia
        
        //establecemos la fecha exacta en qué termina el countdown
        year: 2020,
        month: 12,
        day: 1,
        hour: 09, //formato 24hr
        minute:00,
        second:0,
        
        //Establecemos qué haremos luego que termina el countdown
        onFinish: function () { 
            
        } 
    });
   
});