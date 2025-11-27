import { useState } from "react"
import { useEffect } from "react"
import GameField from "./GameField"
import "./GameManager.css"

const GameManager = () => {
    const count = 9;
    const initial_array = Array(count).fill("none");
    const [states, setStates] = useState(initial_array);
    const [winner, setWinner] = useState("none");
    const [player, setPlayer] = useState("cross");
    const [winner_sign, setSign] = useState("");

    const onRestart = () => {
        setStates(initial_array);
        setWinner("none");
        setPlayer("cross");
    }

    const updateWinner = () => {
        const positions = [
            [0, 1, 2],
            [3, 4, 5],
            [6, 7, 8],
            [0, 3, 6],
            [1, 4, 7],
            [2, 5, 8],
            [0, 4, 8],
            [2, 4, 6]
        ]
        for (let i = 0; i < positions.length; ++i) {
            let arr = positions[i];
            if (states[arr[0]] !== "none" && 
               states[arr[0]] === states[arr[1]] && 
               states[arr[1]] === states[arr[2]]) {
               setWinner(states[arr[0]]);
               setSign(states[arr[0]] === "cross" ? "X" : "O");
            }
        }
    }

    useEffect(updateWinner, [states]);

    const onClick = (index) => {
        if (states[index] !== "none" || winner !== "none") {
            return;
        }
        const array = states.slice();
        array[index] = player;
        setStates(array);
        setPlayer(player === "cross" ? "zero" : "cross");

    }




    return <>
    <h2 className="title"> Play a Game </h2>
    <div className={winner}> <p className="winner"> The winner is {winner_sign} </p> </div>
    <GameField states={states} onClick={onClick}/>
    <button className="restart" onClick={onRestart}> restart </button>
    </>
}

export default GameManager