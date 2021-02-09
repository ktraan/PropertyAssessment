import { useState } from 'react';

const Form = ({ getAssessment }) => {
  const [houseNumber, setHouseNumber] = useState('');
  const [streetName, setStreetName] = useState('');

  const onSubmit = (event) => {
    event.preventDefault();

    // Set values to getAssessment props
    getAssessment({ houseNumber, streetName });
  };

  return (
    <form onSubmit={onSubmit}>
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
