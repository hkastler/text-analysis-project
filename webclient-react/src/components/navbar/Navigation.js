import React from 'react';
import { NavLink } from 'react-router-dom'


class Navigation extends React.Component {
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">

        <a className="navbar-brand" href="/">Twitter Sentiment Analysis</a>

        <button className="navbar-toggler" type="button">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item" >
              <NavLink className="nav-link" activeClassName="nav-link active" to="/home">Home</NavLink>
            </li>

            <li className="nav-item" >
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