import React, { useEffect, useState } from "react";
import axios from "axios";
import {Link} from "react-router-dom";

const Datatable = () => {
    const [vodostaji, setVodostaji] = useState([]);
    const [filteredVodostaji, setFilteredVodostaji] = useState([]);
    const [filter, setFilter] = useState("");
    const [filterField, setFilterField] = useState("all");
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/vodostaji");
                const data = response.data.response;
                setVodostaji(data);
                setFilteredVodostaji(data);
            } catch (err) {
                setError("Neuspjelo dohvaćanje podataka");
            }
        };

        fetchData();
    }, []);

    useEffect(() => {
        if (filter) {
            const filtered = vodostaji.filter((row) => {
                const fieldValue = getFieldValue(row, filterField);

                return filterField === "all"
                    ? Object.values(row).some((value) =>
                        String(value).toLowerCase().includes(filter.toLowerCase())
                    )
                    : fieldValue !== null && String(fieldValue).toLowerCase().includes(filter.toLowerCase());
            });
            setFilteredVodostaji(filtered);
        } else {
            setFilteredVodostaji(vodostaji);
        }
    }, [filter, filterField, vodostaji]);

    const handleFilterChange = (e) => {
        setFilter(e.target.value);
    };

    const handleFilterFieldChange = (e) => {
        setFilterField(e.target.value);
    };

    const getFieldValue = (row, field) => {
        switch (field) {
            case "temperatura_zraka":
                return row.temperatura_zraka;
            case "tlak_zraka":
                return row.tlak_zraka;
            case "oborine":
                return row.oborine;
            case "stanje_vremena":
                return row.stanje_vremena;
            case "vjetar.smjer":
                return row.vjetar ? row.vjetar.smjer : null;
            case "vjetar.brzina":
                return row.vjetar ? row.vjetar.brzina : null;
            default:
                return row[field];
        }
    };

    const exportToCSV = () => {
        const headers = [
            "id",
            "datum",
            "vrijeme",
            "stanica",
            "vodostaj",
            "temperatura_zraka",
            "vlaznost",
            "tlak_zraka",
            "oborine",
            "stanje_vremena",
            "vjetar.smjer",
            "vjetar.brzina"
        ];

        const rows = filteredVodostaji.map((row) => [
            row.id,
            row.datum,
            row.vrijeme,
            row.stanica,
            row.vodostaj,
            row.temperatura_zraka,
            row.vlaznost,
            row.tlak_zraka,
            row.oborine,
            row.stanje_vremena,
            row.vjetar ? row.vjetar.smjer : "",
            row.vjetar ? row.vjetar.brzina : ""
        ]);

        const csvContent = [
            headers.join(","),
            ...rows.map((row) => row.join(","))
        ].join("\n");

        const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.setAttribute("download", "filtered_data.csv");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    const exportToJSON = () => {
        const jsonContent = JSON.stringify(filteredVodostaji, null, 2);
        const blob = new Blob([jsonContent], { type: "application/json" });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.setAttribute("download", "filtered_data.json");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    return (
        <div className="bg-gradient-to-b from-gray-100 to-blue-100 min-h-screen flex flex-col items-center p-8">
            <h1 className="text-3xl md:text-4xl font-bold text-blue-700 mb-8">
                Tablica Podataka Vodostaja
            </h1>

            {error && <p className="text-red-500 mb-4">{error}</p>}

            <div className="mb-6 w-full max-w-md flex space-x-4">
                <select
                    value={filterField}
                    onChange={handleFilterFieldChange}
                    className="px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:border-blue-500"
                >
                    <option value="all">Svi Podaci</option>
                    <option value="stanica">Stanica</option>
                    <option value="datum">Datum</option>
                    <option value="vrijeme">Vrijeme</option>
                    <option value="vodostaj">Vodostaj</option>
                    <option value="temperatura_zraka">Temperatura Zraka</option>
                    <option value="vlaznost">Vlaznost</option>
                    <option value="tlak_zraka">Tlak Zraka</option>
                    <option value="oborine">Oborine</option>
                    <option value="stanje_vremena">Stanje Vremena</option>
                    <option value="vjetar.smjer">Vjetar Smjer</option>
                    <option value="vjetar.brzina">Vjetar Brzina</option>
                </select>

                <input
                    type="text"
                    placeholder="Pretraži podatke..."
                    value={filter}
                    onChange={handleFilterChange}
                    className="w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:border-blue-500"
                />
            </div>

            <div className="flex mb-6 space-x-4">
                <button
                    onClick={exportToCSV}
                    className="px-4 py-2 bg-blue-600 text-white rounded-lg shadow-md hover:bg-blue-700 focus:outline-none"
                >
                    Preuzmi CSV
                </button>
                <button
                    onClick={exportToJSON}
                    className="px-4 py-2 bg-green-600 text-white rounded-lg shadow-md hover:bg-green-700 focus:outline-none"
                >
                    Preuzmi JSON
                </button>
            </div>

            <div className="w-full overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
                <table className="min-w-full bg-white">
                    <thead className="bg-blue-200">
                    <tr>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">ID</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Datum</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Vrijeme</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Stanica</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Vodostaj</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Temperatura Zraka</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Vlaznost</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Tlak Zraka</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Oborine</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Stanje Vremena</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Vjetar Smjer</th>
                        <th className="px-4 py-3 border-b font-semibold text-gray-700">Vjetar Brzina</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredVodostaji.map((row) => (
                        <tr key={row.id} className="text-center border-b hover:bg-blue-50">
                            <td className="px-4 py-3">{row.id}</td>
                            <td className="px-4 py-3">{row.datum}</td>
                            <td className="px-4 py-3">{row.vrijeme}</td>
                            <td className="px-4 py-3">{row.stanica}</td>
                            <td className="px-4 py-3">{row.vodostaj}</td>
                            <td className="px-4 py-3">{row.temperatura_zraka}</td>
                            <td className="px-4 py-3">{row.vlaznost}</td>
                            <td className="px-4 py-3">{row.tlak_zraka}</td>
                            <td className="px-4 py-3">{row.oborine}</td>
                            <td className="px-4 py-3">{row.stanje_vremena}</td>
                            <td className="px-4 py-3">{row.vjetar ? row.vjetar.smjer : "N/A"}</td>
                            <td className="px-4 py-3">{row.vjetar ? row.vjetar.brzina : "N/A"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <Link to="/" className="mt-12 px-4 py-2 bg-blue-600 rounded-xl text-white">
                Natrag na početnu
            </Link>
        </div>
    );
};

export default Datatable;