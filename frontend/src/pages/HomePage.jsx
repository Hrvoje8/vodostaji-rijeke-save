import React from "react";
import { Link } from "react-router-dom";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";

const HomePage = () => {
    const { loginWithRedirect, logout, isAuthenticated, user, getAccessTokenSilently } = useAuth0();

    // Funkcija za osvježavanje preslika
    const refreshFiles = async () => {
        try {
            const token = await getAccessTokenSilently();
            const response = await axios.post("http://localhost:8080/api/download/refresh", {}, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            alert(response.data);
        } catch (error) {
            console.error("Greška prilikom osvježavanja preslika:", error);
            alert("Greška prilikom osvježavanja preslika.");
        }
    };

    // Funkcija za preuzimanje datoteka
    const downloadFile = async (url, filename) => {
        try {
            const token = await getAccessTokenSilently();
            const response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                responseType: "blob",
            });

            const blob = new Blob([response.data], { type: response.headers["content-type"] });
            const link = document.createElement("a");
            link.href = URL.createObjectURL(blob);
            link.setAttribute("download", filename);
            document.body.appendChild(link);
            link.click();
            link.remove();
        } catch (error) {
            console.error("Greška prilikom preuzimanja datoteke:", error);
            alert("Greška prilikom preuzimanja datoteke.");
        }
    };

    return (
        <div className="bg-gradient-to-b from-blue-100 to-white min-h-screen flex flex-col items-center justify-center p-8 text-center">
            {/* Navigacijski bar */}
            <nav className="w-full bg-blue-700 text-white py-4 px-8 flex justify-between items-center">
                <Link to="/" className="text-2xl font-bold">
                    Vodostaji Save
                </Link>
                <div>
                    {!isAuthenticated ? (
                        <button
                            onClick={() => loginWithRedirect()}
                            className="text-white bg-blue-500 px-4 py-2 rounded-lg hover:bg-blue-600"
                        >
                            Prijavi se
                        </button>
                    ) : (
                        <div className="flex items-center space-x-4">
                            <span className="font-medium">Pozdrav, {user.name}!</span>
                            <Link
                                to="/profile"
                                className="text-white bg-gray-600 px-4 py-2 rounded-lg hover:bg-gray-700"
                            >
                                Profil
                            </Link>
                            <button
                                onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}
                                className="text-white bg-red-500 px-4 py-2 rounded-lg hover:bg-red-600"
                            >
                                Odjavi se
                            </button>
                        </div>
                    )}
                </div>
            </nav>

            {/* Glavni sadržaj */}
            <div className="max-w-3xl mx-auto mt-16">
                <h1 className="text-4xl md:text-5xl font-bold text-blue-700 mb-16">
                    Dobrodošli u Bazu Podataka Vodostaja Rijeke Save
                </h1>

                <Link
                    to="/datatable"
                    className="text-lg px-6 py-3
                                        text-white bg-black rounded-xl mt-24 inline-block"
                >
                    Pregledaj Tablicu Podataka
                </Link>

                <div className="mt-8">
                    <h2 className="text-2xl font-bold text-blue-600 mb-4">Preuzmi Skup Podataka</h2>
                    <ul>
                        <li className="my-4">
                            <button
                                onClick={() => downloadFile("http://localhost:8080/api/download/vodostaji.csv", "vodostaji.csv")}
                                className="text-white bg-blue-500 px-4 py-2 rounded-xl hover:bg-blue-600"
                            >
                                Preuzmi CSV format
                            </button>
                        </li>
                        <li className="my-4">
                            <button
                                onClick={() => downloadFile("http://localhost:8080/api/download/vodostaji.json", "vodostaji.json")}
                                className="text-white bg-green-500 px-4 py-2 rounded-xl hover:bg-green-600"
                            >
                                Preuzmi JSON format
                            </button>
                        </li>
                        <li className="my-4">
                            <button
                                onClick={() => downloadFile("http://localhost:8080/api/download/schema.json", "schema.json")}
                                className="text-white bg-orange-500 px-4 py-2 rounded-xl hover:bg-orange-600"
                            >
                                Preuzmi JSON Schema
                            </button>
                        </li>
                    </ul>
                </div>

                <div className="mt-8">
                    <h2 className="text-2xl font-bold text-yellow-600 mb-4">Osvježi Preslike</h2>
                    <button
                        onClick={refreshFiles}
                        className="text-white bg-yellow-500 px-4 py-2 rounded-lg hover:bg-yellow-600"
                    >
                        Osvježi Preslike
                    </button>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
