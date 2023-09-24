import { useState } from "react";

export default function PersonUpdateForm({ person, onSubmit, onCancel }) {
  const [personData, setPersonData] = useState({ ...person });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPersonData({
      ...personData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(personData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        First name:
        <input
          type="text"
          name="firstName"
          value={personData.firstName}
          onChange={handleInputChange}
        />
      </label>

      <label>
        Last name:
        <input
          type="text"
          name="lastName"
          value={personData.lastName}
          onChange={handleInputChange}
        />
      </label>

      <label>
        Birthday:
        <input
          type="text"
          name="birthday"
          value={personData.birthday}
          onChange={handleInputChange}
        />
      </label>

      <label>
        Address:
        <input
          type="text"
          name="address"
          value={personData.address}
          onChange={handleInputChange}
        />
      </label>

      <label>
        City:
        <input
          type="text"
          name="city"
          value={personData.city}
          onChange={handleInputChange}
        />
      </label>

      <label>
        State:
        <input
          type="text"
          name="state"
          value={personData.state}
          onChange={handleInputChange}
        />
      </label>

      <label>
        Zip code:
        <input
          type="number"
          name="zipCode"
          value={personData.zipCode}
          onChange={handleInputChange}
        />
      </label>

      <label>
        Phone number:
        <input
          type="text"
          name="phoneNumber"
          value={personData.phoneNumber}
          onChange={handleInputChange}
        />
      </label>

      <button type="submit">Update</button>
      <button
        type="button"
        onClick={onCancel}
      >
        Cancel
      </button>
    </form>
  );
}
