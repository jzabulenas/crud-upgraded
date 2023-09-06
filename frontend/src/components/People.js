import { useEffect, useState } from 'react';
import "../style.scss";

export default function People() {
    const [people, setPeople] = useState([]);

    useEffect(() => {
        let active = true;

        const fetchData = async () => {
            const response = await fetch(`http://localhost:8080/people`);
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
        <article className='container'>
            {people.map(person => {
                return (
                    <>
                        <p>{person.firstName} {person.lastName}</p>
                    </>
                )
            })}
        </article>
    );
}