import React from 'react';
import { NavLink } from 'react-router-dom'


class Navigation extends React.Component {

  constructor() {
    super();
    this.state = {
      navbarOpen: false
    };
    this.render = this.render.bind(this);
    this.toggleNavbar = this.toggleNavbar.bind(this);
  }

  toggleNavbar() {
    this.setState({
      navbarOpen: !this.state.navbarOpen
    });
  }

  render() {
    let collapseMenuClass = ["collapse navbar-collapse"];
    if (this.state.navbarOpen) {
      collapseMenuClass.push('show');
    }
    return (
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">

        <NavLink className="navbar-brand" to="/">Twitter Sentiment Analysis</NavLink>

        <button className="navbar-toggler" type="button" onClick={this.toggleNavbar}>
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className={collapseMenuClass.join(' ')} >
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