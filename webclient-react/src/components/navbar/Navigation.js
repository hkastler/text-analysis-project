import React from 'react';
import { NavLink } from 'react-router-dom'


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
              <NavLink className="nav-link" activeClassName="nav-link active" to="/home">Home</NavLink>
            </li>

            <li class="nav-item" >
              <NavLink className="nav-link"
                activeClassName="nav-link active" to="/about">About</NavLink>
            </li>
          </ul>
        </div>
      </nav>
    )
  }
}

export default Navigation;