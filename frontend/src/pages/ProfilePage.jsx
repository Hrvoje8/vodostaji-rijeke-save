import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";

const ProfilePage = () => {
    const { getAccessTokenSilently, isAuthenticated, isLoading, user } = useAuth0();
    const [userInfo, setUserInfo] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const token = await getAccessTokenSilently();
                const response = await axios.get(
                    `https://${process.env.REACT_APP_AUTH0_DOMAIN}/userinfo`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
                setUserInfo(response.data);
            } catch (error) {
                console.error("Error fetching user info:", error);
                setError("Neuspjelo dohvaćanje korisničkih podataka.");
            }
        };

        if (isAuthenticated) {
            fetchUserInfo();
        }
    }, [getAccessTokenSilently, isAuthenticated]);

    if (isLoading) {
        return <div className="text-center text-lg">Učitavanje profila...</div>;
    }

    if (error) {
        return <div className="text-center text-red-500">{error}</div>;
    }

    if (!isAuthenticated) {
        return <div className="text-center text-lg">Niste prijavljeni.</div>;
    }

    const displayUserInfo = userInfo || user; // Koristi `user` ako `userInfo` nije dohvaćen.

    return (
        <div className="min-h-screen bg-gradient-to-b from-blue-100 to-white flex flex-col items-center justify-center p-8">
            <div className="bg-white shadow-md rounded-lg p-6 w-full max-w-md">
                <h1 className="text-2xl font-bold text-blue-700 mb-4">Vaš Profil</h1>
                {displayUserInfo?.picture && (
                    <img
                        src={displayUserInfo.picture}
                        alt={displayUserInfo.name}
                        className="w-32 h-32 rounded-full mx-auto mb-4"
                    />
                )}
                <p className="text-lg text-gray-700">
                    <span className="font-bold">Ime:</span> {displayUserInfo.name}
                </p>
                <p className="text-lg text-gray-700">
                    <span className="font-bold">Email:</span> {displayUserInfo.email}
                </p>
            </div>
        </div>
    );
};

export default ProfilePage;
