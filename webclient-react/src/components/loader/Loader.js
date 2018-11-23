import React from 'react';
import './Loader.css';

class Loader extends React.Component {
   
    render() {
        return (
            <div id="loader-wrapper">
                <div id="loader"></div>
                <div className="loader-section section-left"></div>
                <div className="loader-section section-right"></div>
            </div>
        )
    }
}

export default Loader;