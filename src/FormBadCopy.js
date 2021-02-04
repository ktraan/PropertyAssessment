import React, {useState, useEffect} from 'react'
import Form from 'react-bootstrap/Form'
import Row from 'react-bootstrap/Row'
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col'

const URL_ENDPOINT = "https://data.edmonton.ca/resource/q7d6-ambg.json"

function SearchAddress() {
  const [assessment, setAssessment] = useState([])
  const [error, setError] = useState(null)
  const [address, setAddress] = useState([])

  useEffect(() => {
    fetch(URL_ENDPOINT)
    .then(response => response.json())
    .then((data) => {
      setAssessment(data)
    }, (error) => {
      setError(error);
    })
  }, [])

  if (error) {
    return <div>Error: {error.message}</div>;
  } else {
    return (
      <Form className="mt-10 d-flex justify-content-center">
        
          <Col md="4" className="d-flex-row justify-content-start">
          <Form.Label>Edmonton Address</Form.Label>
          <Form.Control name="address" type="text" placeholder="Enter the address here..." />
          <Button variant="primary" onClick={logger}>Submit</Button>
          {/* <Form.Control class="mt-10" size="md" as="select">
            {assessment.filter(item => {
            return item.house_number || item.street_name
            }).sort((a, b) => (a.account_number - b.account_number)).map(item => (
            <option key={item.account_number}>
            {item.house_number} {item.street_name}
            </option>
            ))}
          </Form.Control> */}
          </Col>
      </Form>
      
    )
  }
  function logger() {
    console.log(this.state)
    
  }
}



export default SearchAddress