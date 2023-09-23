import { useState } from "react";

export default function PersonInsertForm({ onCancel, onSubmit }) {
  const handleSubmit = (e) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);
    const formJson = JSON.stringify(Object.fromEntries(formData.entries()));

    fetch("http://localhost:8080/people", {
      method: form.method,
      body: formJson,
      headers: {
        "Content-Type": "application/json",
      },
    });

    onSubmit();
  };

  return (
    <form
      method="post"
      onSubmit={handleSubmit}
    >
      <label>
        First name:
        <input
          type="text"
          name="firstName"
          defaultValue={""}
        />
      </label>

      <label>
        Last name:
        <input
          type="text"
          name="lastName"
          defaultValue={""}
        />
      </label>

      <label>
        Birthday:
        <input
          type="text"
          name="birthday"
          defaultValue={""}
        />
      </label>

      <label>
        Address:
        <input
          type="text"
          name="address"
          defaultValue={""}
        />
      </label>

      <label>
        City:
        <input
          type="text"
          name="city"
          defaultValue={""}
        />
      </label>

      <label>
        State:
        <input
          type="text"
          name="state"
          defaultValue={""}
        />
      </label>

      <label>
        Zip code:
        <input
          type="number"
          name="zipCode"
          defaultValue={""}
        />
      </label>

      <label>
        Phone number:
        <input
          type="text"
          name="phoneNumber"
          defaultValue={""}
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
