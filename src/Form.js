import React, {useState, useEffect} from 'react'
import Form from 'react-bootstrap/Form'

const URL_ENDPOINT = "https://data.edmonton.ca/resource/q7d6-ambg.json"

function SearchAddress() {
  const [assessment, setAssessment] = useState([])
  const [error, setError] = useState(null)

  let assessments = [];

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
      <Form className="mt-10">
        <Form.Control  size="md" as="select">
        {assessment.filter(item => {
          return item.house_number || item.street_name
        }).sort((a, b) => (a.account_number - b.account_number)).map(item => (
          <option key={item.account_number}>
            {item.house_number} {item.street_name}
          </option>
        ))}
        </Form.Control>
      </Form>
      // <select>
      //   {assessment.filter(item => {
      //     return item.house_number || item.street_name
      //   }).sort((a, b) => (a.account_number - b.account_number)).map(item => (
      //     <option key={item.account_number}>
      //       {item.house_number} {item.street_name}
      //     </option>
      //   ))}
      // </select>
      
    )
  }
}

export default SearchAddress