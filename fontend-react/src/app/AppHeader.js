import React, { Component } from 'react';
import { Link, NavLink } from 'react-router-dom';
import './AppHeader.css';

class AppHeader extends Component {
    render() {
        return (
            <header className="app-header">
                <div className="container">
                    <div className="app-branding">
                        <Link to="/" className="app-title">Know Your Neighborhood</Link>
                    </div>
                    <div className="app-options">
                                       
                        <nav className="app-nav">
                                 <ul>
                                        <li>
                                            <NavLink to="/">HOME</NavLink>
                                        </li>
                                        <li>
                                            <NavLink to="/store">STORE</NavLink>
                                        </li>
                                        <li>
                                            <NavLink to="/about">ABOUT US</NavLink>        
                                        </li>
                                        <li>
                                            <NavLink to="/contact"> CONTACT US</NavLink>        
                                        </li>
                                </ul>
                                { this.props.authenticated ? (
                                    <ul>
                                        <li>
                                            <NavLink to="/profile">PROFILE</NavLink>
                                        </li>
                                        <li>
                                            <a onClick={this.props.onLogout}>LOGOUT</a>
                                        </li>
                                    </ul>
                                ): (
                                    <ul>
                                        <li>
                                            <NavLink to="/login">LOGIN</NavLink>        
                                        </li>
                                        <li>
                                            <NavLink to="/signup">REGISTER</NavLink>        
                                        </li>
                                    </ul>
                                )}
                                
                                       
                                
                        </nav>
                    </div>
                </div>
            </header>
        )
    }
}

export default AppHeader;