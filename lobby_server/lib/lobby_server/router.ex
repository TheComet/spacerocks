defmodule LobbyServer.Router do
  use Plug.Router

  plug Plug.Logger
  plug :match
  plug :dispatch

  get "/games", do: LobbyServer.MyPipeline.games(conn, 2)
  match _, do: send_resp(conn, 404, "Oops!")
end

