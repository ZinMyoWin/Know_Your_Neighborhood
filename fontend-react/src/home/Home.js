import React, { Component } from 'react';
import './Home.css';
import home2 from '../img/home2.png';


class Home extends Component {
    render() {
        return (
            <div className="home-container">
                    <h1 className="home-title">Welcome to Know Your Neighborhood </h1>
                    <div className="container">
                        
                        <img src={home2} alt="Online Store"></img>
                    </div>
            </div>
        )
    }
}

export default Home;