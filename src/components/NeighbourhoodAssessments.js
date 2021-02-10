import { useState, useEffect } from 'react';

const NeighbourhoodAssessments = ({ assessment }) => {
  const [neighbourhoodAssessments, setAssessment] = useState([]);

  const BASE_URL_ENDPOINT = 'https://data.edmonton.ca/resource/q7d6-ambg.json?';

  const fetchAssessmentByNeighbourhood = async () => {
    const res = await fetch(
      `${BASE_URL_ENDPOINT}neighbourhood=${assessment.neighbourhood}`
    );
    const data = await res.json();

    console.log(data);
  };

  const logger = () => {
    console.log(assessment);
  };

  return (
    <div>
      <h4>Other assessments in the area</h4>
      <button onClick={fetchAssessmentByNeighbourhood}>Log</button>
    </div>
  );
};

export default NeighbourhoodAssessments;
