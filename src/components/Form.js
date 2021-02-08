import { useState } from 'react';

const Form = ({ getAssessment }) => {
  const [houseNumber, setHouseNumber] = useState('');
  const [streetName, setStreetName] = useState('');
  const [errors, setErrors] = useState('');

  const onSubmit = (event) => {
    event.preventDefault();

    // Handle empty values
    if (!houseNumber || !streetName) {
      setErrors('Please enter all of the fields before submitting.');
    } else {
      setErrors('');
    }

    // Set values to getAssessment props
    getAssessment({ houseNumber, streetName, errors });
  };

  return (
    <form onSubmit={onSubmit}>
      {errors ? (
        <div className='alert alert-danger'>
          <strong>{errors}</strong>
        </div>
      ) : (
        ''
      )}
      <div>
        <label>House Number:</label>
        <input
          type='text'
          value={houseNumber}
          placeholder='eg. 13013'
          onChange={(e) => setHouseNumber(e.target.value)}
        />
        <br />
        <label className='mt-2'>Street Name:</label>
        <input
          type='text'
          value={streetName}
          placeholder='eg. 107 Street NW'
          onChange={(e) => setStreetName(e.target.value)}
        />
        <br />
        <button
          className='btn btn-primary mt-3 justify-content-end ml-4'
          type='submit'
        >
          Get Assessment
        </button>
      </div>
    </form>
  );
};

export default Form;
