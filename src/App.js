import React from 'react'
import Form from './Form'
import Jumbotron from 'react-bootstrap/Jumbotron'
import Row from 'react-bootstrap/Row'

import 'bootstrap/dist/css/bootstrap.css';

function App() {
  return(
    <>
    <Row>
      <Jumbotron>
        <h1 className="mb-16">Property Assessment</h1>
        <h2>Select the Edmonton address you would like more information on!</h2>
      </Jumbotron>
    </Row>
    
    <Form/>
    </>
  )
}

export default App;
