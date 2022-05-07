package br.com.study.quarkus;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    public List<Produto> buscarTodosOsProdutos(){
        return Produto.listAll();
    }

    @POST
    @Transactional
    public void salvarProduto(CadastrarProdutoDTO produtoDTO){

        Produto produto = new Produto();
        produto.nome = produtoDTO.nome;
        produto.valor = produtoDTO.valor;

        produto.persist();

        Produto.listAll();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void alterarProduto(@PathParam("id") Long id, CadastrarProdutoDTO produtoDTO){

        Optional<Produto> optionalProduto = Produto.findByIdOptional(id);

        if (optionalProduto.isPresent()){
            Produto produto = optionalProduto.get();
            produto.nome = produtoDTO.nome;
            produto.valor = produtoDTO.valor;
            produto.persist();
        }else {
            throw new NotFoundException();
        }
        Produto.listAll();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletarProduto(@PathParam("id") Long id, CadastrarProdutoDTO produtoDTO){

        Optional<Produto> optionalProduto = Produto.findByIdOptional(id);

        optionalProduto.ifPresentOrElse(Produto::delete,
                () ->{
                        throw new NotFoundException();
        });

    }
}
