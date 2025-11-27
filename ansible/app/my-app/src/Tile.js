const Tile = ({state, onClick, index}) => {
  return <>
    <div className="tile" onClick={() => {onClick(index)}}>
        <p className={state}> {state === "cross" ? "X" : "O"} </p>
    </div>
  
  </>
}

export default Tile;