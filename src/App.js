import React from 'react';
import { useState, useEffect } from 'react';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Row from 'react-bootstrap/Row';
import Form from './components/Form';

function App() {
  const [assessment, setAssessment] = useState('');
  const [address, setAddress] = useState({
    houseNumber: '',
    streetName: '',
  });

  const BASE_URL_ENDPOINT = 'https://data.edmonton.ca/resource/q7d6-ambg.json?';

  // Get assessment from props
  const getAssessment = async (props) => {
    const houseNumber = props.houseNumber;
    const streetName = props.streetName.toUpperCase();

    // Set the full address to state
    setAddress({ houseNumber: houseNumber, streetName: streetName });

    fetchAssessment(houseNumber, streetName);
  };

  // Fetch assessment
  const fetchAssessment = async (houseNumber, streetName) => {
    const res = await fetch(
      `${BASE_URL_ENDPOINT}house_number=${houseNumber}&street_name=${streetName}`
    );
    console.log(res);
    const data = await res.json();
    console.log(data);
  };

  return (
    <div className='container'>
      <Row className='d-flex justify-content-center'>
        <Jumbotron className=''>
          <h1 className='mb-16'>Property Assessment</h1>
          <h2>
            Enter the Edmonton address you would like more information on!
          </h2>
        </Jumbotron>
      </Row>
      <Form getAssessment={getAssessment} />
    </div>
  );
}

export default App;
