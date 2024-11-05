import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage";
import Datatable from "./pages/Datatable";

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/datatable" element={<Datatable />} />
        </Routes>
      </Router>
  );
}

export default App;