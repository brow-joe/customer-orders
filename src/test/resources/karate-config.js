function() {
    karate.configure('connectTimeout', 100000);
    karate.configure('readTimeout', 100000);

    var application = java.lang.System.getProperty('server.application');
    var token = java.lang.System.getProperty('server.authorization');
    
    var authorization = 'Basic ' + token;
        
    var config = {
        application: application,
        authorization: authorization
    };

    karate.log('karate host: ', JSON.stringify(config.application));
    karate.log('karate authorization: ', JSON.stringify(config.authorization));

    karate.configure('headers', {
        Authorization: config.authorization
    });

    return config;
}
