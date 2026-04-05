import { useParams } from "react-router-dom";

const ProductDetail = () =>{
    const { id } = useParams<{ id: string }>();
    return <> Exibindo detalhes do produto: {id} </>
}

export default ProductDetail