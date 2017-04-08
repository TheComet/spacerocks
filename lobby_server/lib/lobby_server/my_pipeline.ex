defmodule LobbyServer.MyPipeline do
  # We use Plug.Builder to have access to the plug/2 macro.
  # This macro can receive a function or a module plug and an
  # optional parameter that will be passed unchanged to the
  # given plug.
  use Plug.Builder

  def games(conn, _) do
    conn
      |> send_resp(
        200,
        Poison.encode!(%GameList{games: games()})
      )
  end

  defp games do
    [
      %Game{id: 42}
    ]
  end
end
