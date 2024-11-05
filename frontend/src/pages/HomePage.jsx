import React from "react";
import { Link } from "react-router-dom";

const HomePage = () => {
    return (
        <div className="bg-gradient-to-b from-blue-100 to-white min-h-screen flex flex-col items-center justify-center p-8 text-center">
            <div className="max-w-3xl mx-auto">
                <h1 className="text-4xl md:text-5xl font-bold text-blue-700 mb-16">
                    Dobrodošli u Bazu Podataka Vodostaja Rijeke Save
                </h1>
                <Link to="/datatable" className="text-lg px-6 py-3 text-white bg-black rounded-xl mt-24">
                    Pregledaj Tablicu Podataka
                </Link>

                <div className="mt-8">
                    <h2 className="text-2xl font-bold text-blue-600 mb-4">Preuzmi Skup Podataka</h2>
                    <ul>
                        <li className="my-4">
                            <a href="http://localhost:8080/api/download/vodostaji.csv" download
                               className="text-white bg-blue-500 px-4 py-2 rounded-xl hover:underline">
                                Preuzmi CSV format
                            </a>
                        </li>
                        <li className="my-4">
                            <a href="http://localhost:8080/api/download/vodostaji.json" download
                               className="text-white bg-green-500 px-4 py-2 rounded-xl hover:underline">
                                Preuzmi JSON format
                            </a>
                        </li>
                        <li className="my-4">
                            <a href="http://localhost:8080/api/download/schema.json" download
                               className="text-white bg-orange-500 px-4 py-2 rounded-xl hover:underline">
                                Preuzmi JSON Schema
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
