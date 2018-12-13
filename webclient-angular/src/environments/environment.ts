import PROPERTIES from './service-properties.js';
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  twitterSentimentAnalysisURL: PROPERTIES.scheme + "://" + getHostname(PROPERTIES.hostname) + ":" + PROPERTIES.port +  PROPERTIES.serviceUrl, 
};

function getHostname(hostnamePropVal){
  var hostname = hostnamePropVal;
  if(hostname === 'window.location.hostname'){
    hostname = window.location.hostname;
  }
  return hostname;
}

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
