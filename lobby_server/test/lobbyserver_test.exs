defmodule LobbyserverTest do
  use ExUnit.Case
  doctest Lobbyserver

  test "the truth" do
    assert 1 + 1 == 2
  end

  test "thing" do
    assert Lobbyserver.hello == :world
  end
end
