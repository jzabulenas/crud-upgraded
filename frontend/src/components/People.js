import { useEffect, useState } from 'react';
import "../style.scss";
import "./People.scss";

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

    function updateUser(id) {

    }

    return (
        <article className='container'>
            <table className='table responsive'>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Birthday</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Zip code</th>
                        <th>Phone number</th>
                        <th>Delete</th>
                    </tr>
                </thead>

                <tbody>
                    {people.map(person => {
                        return (
                            <tr key={person.id}>
                                <td data-label="id">{person.id}</td>
                                <td data-label="First name">{person.firstName}</td>
                                <td data-label="Last name">{person.lastName}</td>
                                <td data-label="Birthday">{person.birthday}</td>
                                <td data-label="Address">{person.address}</td>
                                <td data-label="City">{person.city}</td>
                                <td data-label="State">{person.state}</td>
                                <td data-label="Zip code">{person.zipCode}</td>
                                <td data-label="Phone number">{person.phoneNumber}</td>
                                <td data-label="Update"><button onClick={updateUser(person.id)}>Delete</button></td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        </article >
    );
}