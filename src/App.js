import React from 'react';
import { useState, useEffect, useRef } from 'react';
import './styles/index.css';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image';
import Table from 'react-bootstrap/Table';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';

import Form from './components/Form';
import NearbyAssessments from './components/NearbyAssessments';

import edmontonCity from './assets/edmonton.jpg';

function App() {
  const [address, setAddress] = useState({
    houseNumber: '',
    streetName: '',
  });
  const [assessment, setAssessment] = useState('');
  const [neighbourhood, setNeighbourhood] = useState('');
  const [neighbourhoodAssessments, setNeighbourhoodAssessments] = useState([]);
  const [errors, setErrors] = useState('');

  const BASE_URL_ENDPOINT = 'https://data.edmonton.ca/resource/qi6a-xuwt.json?';

  useEffect(() => {
    // Neighbourhood is our dependancy,
    // If we change the neighbourhood useEffect will mount it again & re-render
  }, [assessment]);

  // Get assessment from props
  const getAssessment = async (props) => {
    let houseNumber;
    let streetName;

    // Set the full address to state
    if (props.houseNumber === '' || props.streetName === '') {
      setErrors('Please enter the values before submitting.');
    } else {
      setErrors('');
      houseNumber = props.houseNumber;
      streetName = props.streetName.toUpperCase();
      // Set the full address to state
      setAddress({ houseNumber: houseNumber, streetName: streetName });
      fetchAssessment(houseNumber, streetName);
    }
  };

  // Fetch assessment
  const fetchAssessment = async (houseNumber, streetName) => {
    const res = await fetch(
      `${BASE_URL_ENDPOINT}house_number=${houseNumber}&street_name=${streetName}`
    );
    const data = await res.json();

    if (data.length === 0) {
      setErrors(
        `No property assessment found for ${houseNumber} ${streetName}. Did you forget the direction? eg. NW`
      );
    } else {
      setAssessment(data[0]);
      setErrors('');
      setNeighbourhood(data[0].neighbourhood_name);
    }
  };

  // const fetchNeighbourhoodAssessments = async () => {
  //   const res = await fetch(
  //     `${BASE_URL_ENDPOINT}neighbourhood_name=${neighbourhood}`
  //   );
  //   let data = await res.json();

  //   data = data.slice(0, 10);

  //   console.log(data);
  //   setNeighbourhoodAssessments(data);
  // };

  let formatter = new Intl.NumberFormat('en-us', {
    style: 'currency',
    currency: 'USD',
  });

  return (
    <Container className='edmonton-background'>
      <Image className='w-100' src={edmontonCity} alt='Edmonton'></Image>
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
        {assessment && (
          <div className='col-lg-8 align-self-center'>
            <h2>
              <i>Property Assessment for:</i> {assessment.house_number}{' '}
              {assessment.street_name}
            </h2>
          </div>
        )}
      </Row>

      {assessment && (
        <Tabs className='mt-3' defaultActiveKey='assessment'>
          <Tab eventKey='assessment' title='Assessment'>
            {assessment ? (
              <Table className='mt-3 mb-5' bordered hover responsive='lg'>
                <thead>
                  <tr>
                    <th>Address</th>
                    <th>Neighbourhood</th>
                    <th>Garage</th>
                    <th>Property Type</th>
                    <th>Assessment Value</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>
                      {assessment.house_number} {assessment.street_name}
                    </td>
                    <td>
                      {assessment.neighbourhood_name.charAt(0).toUpperCase() +
                        assessment.neighbourhood_name.slice(1).toLowerCase()}
                    </td>
                    <td>
                      {assessment.garage === 'Y' ? (
                        <input type='checkbox' checked={true} readOnly />
                      ) : (
                        <input type='checkbox' checked={false} readOnly />
                      )}
                    </td>
                    <td>
                      {assessment.mill_class_1.charAt(0).toUpperCase() +
                        assessment.mill_class_1.slice(1).toLowerCase()}
                    </td>
                    <td>{formatter.format(assessment.assessed_value)}</td>
                  </tr>
                </tbody>
              </Table>
            ) : (
              ''
            )}
          </Tab>
          {/* <Tab eventKey='nearAssessments' title='Nearby Assessments'>
            <h5 className='mt-3'>
              Assessments in the
              <strong>
                {' ' +
                  neighbourhood.charAt(0).toUpperCase() +
                  neighbourhood.slice(1).toLowerCase() +
                  ' '}
              </strong>
              area
            </h5>

            {neighbourhoodAssessments && (
              <Table className='mt-3 mb-5' bordered hover responsive='lg'>
                <thead>
                  <tr>
                    <th>Address</th>
                    <th>Neighbourhood</th>
                    <th>Garage</th>
                    <th>Property Type</th>
                    <th>Assessment Value</th>
                  </tr>
                </thead>
                {neighbourhoodAssessments.map((x, index) => (
                  <tbody key={x.house_number}>
                    <tr>
                      <td>
                        {x.house_number} {x.street_name}
                      </td>
                      <td>
                        {x.neighbourhood_name.charAt(0).toUpperCase() +
                          x.neighbourhood_name.slice(1).toLowerCase()}
                      </td>
                      <td>
                        {x.garage === 'Y' ? (
                          <input type='checkbox' checked={true} readOnly />
                        ) : (
                          <input type='checkbox' checked={false} readOnly />
                        )}
                      </td>
                      <td>
                        {x.mill_class_1.charAt(0).toUpperCase() +
                          x.mill_class_1.slice(1).toLowerCase()}
                      </td>
                      <td>{formatter.format(x.assessed_value)}</td>
                    </tr>
                  </tbody>
                ))}
              </Table>
            )}
          </Tab> */}
        </Tabs>
      )}
    </Container>
  );
}

export default App;
