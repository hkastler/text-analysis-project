import React from 'react';



class Navigation extends React.Component {
  render() {
    return (
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">

        <a class="navbar-brand" href="/#">Twitter Sentiment Analysis</a>

        <button class="navbar-toggler" type="button">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" ng-controller="navCtrl">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item" >
              <a class="nav-link" href="#!/">Home</a>
            </li>

            <li class="nav-item" >
              <a class="nav-link" href="#!/about">About</a>
            </li>
          </ul>
        </div>
      </nav>
    )
  }
}

export default Navigation;