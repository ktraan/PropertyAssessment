import { useState } from 'react';

const Form = () => {
  const [houseNumber, setHouseNumber] = useState('');
  const [streetName, setStreetName] = useState('');
  return (
    <div>
      <label>House Number:</label>
      <input className='' type='text' placeholder='eg. 13013' />
      <br />
      <label>Street Name:</label>
      <input className='' type='text' placeholder='eg. 107 Street NW' />
      <br />
      <button className='btn btn-primary' type='submit'>
        Get Assessment
      </button>
    </div>
  );
};

export default Form;
