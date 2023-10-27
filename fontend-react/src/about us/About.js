import React, { Component } from 'react'
import aboutus from '../img/aboutus.jpg'
import aboutus2 from '../img/aboutus2.jpg'
import './Aboutus.css'

export class About extends Component {
  render() {
    return (
      <>
      <div className='aboutus-container1'>
        <div className='text-container1'>
        <h1 className='aboutus-header'>About Us</h1>
        <p className='wel-msg'>Welcome to KYN, your go-to destination for exploring and connecting with your neighborhood. We are passionate about creating a sense of community and helping you discover the hidden gems right in your backyard.</p>
        </div>
        <div className='img-container1'>
        <img src={aboutus} alt='aboutus'></img>      
        </div>
      </div>
      <div className='aboutus-container1'>
        <div className='img-container1'>
        <img src={aboutus2} alt='aboutus'></img>      
        </div>
        <div className='text-container2'>
        <h1 className='aboutus-header2'>Our Story</h1>
        <p className='wel-msg'>Founded in 2020, KYN was born out of a desire to foster stronger community bonds and make everyday life in your neighborhood more exciting and convenient. We believe that by providing access to local businesses and services, we can enhance your neighborhood experience.</p>
        <a href='/terms' className='terms-and-conditions'>Terms and Conditions</a>
        </div>
      </div>


      </>
      
    )
  }
}

export default About
