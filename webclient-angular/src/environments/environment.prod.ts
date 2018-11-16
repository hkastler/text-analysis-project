
export const environment = {
  production: true,
  twitterSentimentAnalysisURL: createTwitterSentimentAnalysisURL()  
};

function createTwitterSentimentAnalysisURL(){
  var port = window.location.href.indexOf("https://") == 0 ? "8443" : "8080";
  var serviceLoc = "//" + window.location.hostname + ":" + port;
  var serviceUrl = '/text-analysis-service/rest/twittersa/sa/';
  return serviceLoc + serviceUrl;
}
