package com.unisinos.modelsimulator;

import java.util.ArrayList;
import java.util.List;

import com.unisinos.petri.Arc;
import com.unisinos.petri.PetriNet;
import com.unisinos.petri.Place;
import com.unisinos.petri.Transition;

public class Waiter {

  private PetriNet petrinet;
  private Place substituirCaixa;
  private Place pedidoPronto;
  private Place clienteVaiSentar;

  public Waiter() {
    Place garcomLivre = new Place("garcomLivre");
    Place substituirCaixa = new Place("substituirCaixa");
    this.substituirCaixa = substituirCaixa;
    Place pedidoPronto = new Place("pedidoPronto");
    this.pedidoPronto = pedidoPronto;
    Place clienteVaiSentar = new Place("clienteVaiSentar");
    this.clienteVaiSentar = clienteVaiSentar;
    Place garcomNoCaixa = new Place("garcomNoCaixa");
    Place caixaRetornou = new Place("caixaRetornou");
    Place levandoPedido = new Place("levandoPedido");
    Place pedidoNaMesa = new Place("pedidoNaMesa");
    Place higienizandoMesa = new Place("higienizandoMesa");
    Place mesaHigienizada = new Place("mesaHigienizada");
    List<Place> places = new ArrayList<Place>();
    places.add(garcomLivre);
    places.add(substituirCaixa);
    places.add(pedidoPronto);
    places.add(clienteVaiSentar);
    places.add(garcomNoCaixa);
    places.add(caixaRetornou);
    places.add(levandoPedido);
    places.add(pedidoNaMesa);
    places.add(higienizandoMesa);
    places.add(mesaHigienizada);


    Transition mandarGarcomProCaixa = new Transition("mandarGarcomProCaixa");
    Transition mandarGarcomLevarPedido = new Transition("mandarGarcomLevarPedido");
    Transition mandarGarcomHigienizarMesa = new Transition("mandarGarcomHigienizarMesa");
    Transition voltarDoCaixa = new Transition("voltarDoCaixa");
    Transition voltarDeLevarPedido = new Transition("voltarDeLevarPedido");
    Transition voltarDeHigienizarMesa = new Transition("voltarDeHigienizarMesa");

    List<Transition> transitions = new ArrayList<Transition>();
    transitions.add(mandarGarcomProCaixa);
    transitions.add(mandarGarcomLevarPedido);
    transitions.add(mandarGarcomHigienizarMesa);
    transitions.add(voltarDoCaixa);
    transitions.add(voltarDeLevarPedido);
    transitions.add(voltarDeHigienizarMesa);

    Arc garcomLivreIrParaOCaixa = new Arc(garcomLivre, mandarGarcomProCaixa);
    Arc garcomLivreLevarPedido = new Arc(garcomLivre, mandarGarcomLevarPedido);
    Arc garcomLivreHigienizarMesa = new Arc(garcomLivre, mandarGarcomHigienizarMesa);

    Arc substituirCaixaParaMandarGarcomProCaixa = new Arc(substituirCaixa, mandarGarcomProCaixa);
    Arc pedidoProntoParaMandarGarcomLevarPedido = new Arc(pedidoPronto, mandarGarcomLevarPedido);
    Arc clienteVaiSentarParaMandarGarcomHigienizarMesa = new Arc(clienteVaiSentar, mandarGarcomHigienizarMesa);

    Arc setarGarcomNoCaixa = new Arc(mandarGarcomProCaixa, garcomNoCaixa);
    Arc setarGarcomLevandoPedido = new Arc(mandarGarcomLevarPedido, levandoPedido);
    Arc setarGarcomHigienizandoMesa = new Arc(mandarGarcomHigienizarMesa, higienizandoMesa);

    Arc garcomNoCaixaParaVoltarDoCaixa = new Arc(garcomNoCaixa, voltarDoCaixa);
    Arc levandoPedidoVoltarDeLevarPedido = new Arc(levandoPedido, voltarDeLevarPedido);
    Arc higienizandoMesaVoltarDeHigienizarMesa = new Arc(higienizandoMesa, voltarDeHigienizarMesa);

    Arc caixaRetornouParaVoltarDoVaixa = new Arc(caixaRetornou, voltarDoCaixa);
    Arc pedidoNaMesaParaVoltarDeLevarPedido = new Arc(pedidoNaMesa, voltarDeLevarPedido);
    Arc mesaHigienizadaParaVoltarDeHigienizarMesa = new Arc(mesaHigienizada, voltarDeHigienizarMesa);

    Arc voltarDoCaixaGarcomLivre = new Arc (voltarDoCaixa, garcomLivre);
    Arc voltarDeLevarPedidoGarcomLivre = new Arc(voltarDeLevarPedido, garcomLivre);
    Arc voltarDeHigienizarMesaGarcomLivre = new Arc(voltarDeHigienizarMesa, garcomLivre);

    List<Arc> arcs = new ArrayList<Arc>();

    arcs.add(garcomLivreIrParaOCaixa);
    arcs.add(garcomLivreLevarPedido);
    arcs.add(garcomLivreHigienizarMesa);
    arcs.add(substituirCaixaParaMandarGarcomProCaixa);
    arcs.add(pedidoProntoParaMandarGarcomLevarPedido);
    arcs.add(clienteVaiSentarParaMandarGarcomHigienizarMesa);
    arcs.add(setarGarcomNoCaixa);
    arcs.add(setarGarcomLevandoPedido);
    arcs.add(setarGarcomHigienizandoMesa);
    arcs.add(garcomNoCaixaParaVoltarDoCaixa);
    arcs.add(levandoPedidoVoltarDeLevarPedido);
    arcs.add(higienizandoMesaVoltarDeHigienizarMesa);
    arcs.add(caixaRetornouParaVoltarDoVaixa);
    arcs.add(pedidoNaMesaParaVoltarDeLevarPedido);
    arcs.add(mesaHigienizadaParaVoltarDeHigienizarMesa);
    arcs.add(voltarDoCaixaGarcomLivre);
    arcs.add(voltarDeLevarPedidoGarcomLivre);
    arcs.add(voltarDeHigienizarMesaGarcomLivre);

    this.petrinet = new PetriNet(places, transitions, arcs);
  }

  public void addTokenSubstituirCaixa() {
    this.substituirCaixa.addToken();
  }

  public void addTokenPedidoPronto() {
    this.pedidoPronto.addToken();
  }

  public void addTokenClienteVaiSentar() {
    this.clienteVaiSentar.addToken();
  }
}
