import React from "react";
import { BrowserRouter as Router, Route, Routes, useNavigate } from "react-router-dom";
import { Auth0Provider, useAuth0 } from "@auth0/auth0-react";
import HomePage from "./pages/HomePage";
import Datatable from "./pages/Datatable";
import ProfilePage from "./pages/ProfilePage";

const Callback = () => {
    const { isLoading, error } = useAuth0();
    const navigate = useNavigate();

    React.useEffect(() => {
        if (!isLoading && !error) {
            navigate("/"); // Preusmjeravanje na početnu stranicu nakon uspješne prijave
        }
    }, [isLoading, error, navigate]);

    if (isLoading) {
        return <div>Redirecting...</div>;
    }

    if (error) {
        return <div>There was an error during authentication.</div>;
    }

    return null;
};

function App() {
    const domain = process.env.REACT_APP_AUTH0_DOMAIN;
    const clientId = process.env.REACT_APP_AUTH0_CLIENT_ID;

    return (
        <Auth0Provider
            domain={domain}
            clientId={clientId}
            authorizationParams={{
                audience: process.env.REACT_APP_AUTH0_AUDIENCE, // Tvoj API
                scope: "openid profile email read:vodostaji",       // Npr. "read:vodostaji"
                redirect_uri: window.location.origin + "/callback",
            }}
        >
            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/datatable" element={<Datatable />} />
                    <Route path="/callback" element={<Callback />} />
                    <Route path="/profile" element={<ProfilePage />} />
                </Routes>
            </Router>
        </Auth0Provider>
    );
}

export default App;