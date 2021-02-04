import React from 'react';
import { useState } from 'react';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Row from 'react-bootstrap/Row';
import Form from './components/Form';

function App() {
  const [address, setAddress] = useState('');

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
      <Form />
    </div>
  );
}

export default App;
