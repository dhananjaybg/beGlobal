var beGlobal = (function () {

  //static vars//must not be here is the javascript BT heck this is a demo.
  //idleally pass it as variable  
  var  apikey  = '5GgitIBsBgbRlXXgPK%2BzEw%3D%3D';
  var  bgurl = 'https://api.beglobal.com/translate';      


  // privates
  var v_beGlobal = []; 

  function bgtranslate_private( callback ){

    //set the api key if not given
    if ( v_beGlobal[0].apikey == ''){
      apikey = '5GgitIBsBgbRlXXgPK%2BzEw%3D%3D';      
    }
    //set the url if not given
    if ( v_beGlobal[0].bgurl == ''){
      bgurl = 'https://api.beglobal.com/translate';         
    }
    
    var json_data = {};    
    //enrich json data here 
    json_data["text"] = v_beGlobal[0].src_text;
    json_data["from"] = v_beGlobal[0].src_lang;
    json_data["to"] = v_beGlobal[0].dest_lang;
    
    $.ajax({
      type: 'POST',
      url: bgurl,
      data: JSON.stringify(json_data),                                
      dataType: 'json',     
      contentType: "application/json",
      headers: { "Authorization" : "BeGlobal apiKey=" + apikey, "Content-Type" : "application/json" },

      success: function( jsnResponse ) {                              
        callback( jsnResponse.translation );

      },
      error: function (xmlHttpRequest, textStatus, errorThrown) {
              var ErrResp = "error : " + textStatus + ", errorThrown = " + errorThrown+ "this.reponseText = " + this.responseText;              
              callback(ErrResp);
            }
        });     
    
  }



  // Return an object exposed to the public
  return { 

    // Add items to our basket
    setUp: function( values ) {      
      v_beGlobal.push(values);
    },

    // Get the api key
    getapikey: function () {
      return v_beGlobal[0].apikey;      
    },

    // Get the url
    getapiurl: function () {
      return v_beGlobal[0].bgurl;      
    },    

    // Public alias to a  private function
    //doSomething: doSomethingPrivate,

    bgtranslate: bgtranslate_private

  };
}());

