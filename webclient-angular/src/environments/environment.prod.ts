
export const environment = {
  production: true,
  twitterSentimentAnalysisURL: createTwitterSentimentAnalysisURL()  
};

function createTwitterSentimentAnalysisURL(){
  var serviceLoc = "//" + window.location.hostname;
  var serviceUrl = '/text-analysis-service/rest/twittersa/sa/';
  return serviceLoc + serviceUrl;
}
