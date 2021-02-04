import React from 'react';
import { useState } from 'react';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Row from 'react-bootstrap/Row';
import Form from './components/Form';

function App() {
  const [address, setAddress] = useState('');
  const BASE_URL_ENDPOINT = 'https://data.edmonton.ca/resource/q7d6-ambg.json?';

  const getAssessment = async (props) => {
    const fullAddress = `${props.houseNumber} ${props.streetName}`;
    setAddress(fullAddress);
    console.log(address);

    // setAddress(`${props.houseNumber} ${props.streetName}`);
    // console.log(`Full address: ${address}`);
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
