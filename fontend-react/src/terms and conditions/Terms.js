import React, { Component } from 'react'
import '../terms and conditions/Terms.css'
export class Terms extends Component {
  render() {
    return (
      <>
        <div className='terms-container'>
          <h1>Terms and Conditions</h1>
          <p className='text1'>Welcome to Know-Your-Neighbourhood (KYN)! Before using our application, please carefully review the following Terms and Conditions. By accessing or using KYN, you agree to comply with and be bound by these terms. If you do not agree with any part of these terms, please refrain from using our services.</p>
          <h2>1. Acceptance of Terms</h2>
          <p className='text1'>By accessing or using KYN, you confirm that you have read, understood, and agreed to these Terms and Conditions, as well as our Privacy Policy. These documents govern your use of the application and any related services.</p>
          <h2>2. User Eligibility</h2>
          <p className='text1'>You must be at least [age] years old to use KYN. By using our services, you represent and warrant that you meet this age requirement and that all the information you provide is accurate and complete.</p>
          <h2>3.Privacy</h2>
          <p className='text1'>Your use of KYN is also governed by our Privacy Policy, which explains how we collect, use, and protect your personal information. Please review our Privacy Policy to understand our data practices.</p>


        </div>
      </>
    )
  }
}

export default Terms
