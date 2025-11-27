import Tile from "./Tile"

const GameField = ({states, onClick}) => {

  return <>
  <div className="field">
    <div>
        <Tile state={states[0]} onClick={onClick} index={0}/>
        <Tile state={states[1]} onClick={onClick} index={1}/>
        <Tile state={states[2]} onClick={onClick} index={2}/>
    </div>

    <div>
        <Tile state={states[3]} onClick={onClick} index={3}/>
        <Tile state={states[4]} onClick={onClick} index={4}/>
        <Tile state={states[5]} onClick={onClick} index={5}/>
    </div>

    <div>
        <Tile state={states[6]} onClick={onClick} index={6}/>
        <Tile state={states[7]} onClick={onClick} index={7}/>
        <Tile state={states[8]} onClick={onClick} index={8}/>
    </div>

  </div>
  </>
}

export default GameField;