import logo from './logo.svg';
import './App.css';
import { useEffect, useState } from 'react';

function App() {
  const [people, setPeople] = useState([]);

  useEffect(() => {
    let active = true;

    const fetchData = async () => {
      const response = await fetch(`http://localhost:8080/api/people`);
      const data = await response.json();

      if (active) {
        setPeople(data);
      }
    };

    fetchData();
    return () => {
      active = false;
    };
  }, []);

  return (
    <div>
      {people.map(person => {
        return (
          <>
            <p>{person.firstName} {person.lastName}</p>
          </>
        )
      })}
    </div>
  );
}

export default App;
