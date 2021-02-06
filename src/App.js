import React from 'react';
import { useState } from 'react';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Form from './components/Form';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

function App() {
  const [assessment, setAssessment] = useState('');
  const [address, setAddress] = useState({
    houseNumber: '',
    streetName: '',
  });
  const [errors, setErrors] = useState('');

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
    const data = await res.json();

    if (data.length === 0) {
      setErrors(
        `No property assessment found for ${houseNumber} ${streetName}`
      );
    } else {
      setAssessment(data[0]);
      setErrors('');
    }
  };

  return (
    <Container>
      <Jumbotron fluid>
        <h1 className='ml-4'>Edmonton Property Assessment</h1>

        <h4 className='ml-4 mt-3'>
          Enter the Edmonton address you would like more information on!
        </h4>
      </Jumbotron>
      {errors ? (
        <div className='alert alert-warning'>
          <strong>{errors} </strong>
        </div>
      ) : (
        ''
      )}

      <Row>
        <div className='col-lg-3'>
          <Form getAssessment={getAssessment} />
        </div>
        <div className='col-lg-3'>{assessment.house_number}</div>
      </Row>
    </Container>
  );
}

export default App;
